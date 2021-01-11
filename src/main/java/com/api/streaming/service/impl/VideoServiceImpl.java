package com.api.streaming.service.impl;

import com.api.streaming.config.StorageProperties;
import com.api.streaming.controller.VideoController;
import com.api.streaming.exception.AccessDeniedException;
import com.api.streaming.exception.FailChargeException;
import com.api.streaming.exception.IncorrectFileException;
import com.api.streaming.model.Clasification;
import com.api.streaming.model.User;
import com.api.streaming.model.Video;
import com.api.streaming.model.VideoClasification;
import com.api.streaming.service.UserService;
import com.api.streaming.service.VideoClasificationService;
import com.api.streaming.util.TokenGenerator;
import com.api.streaming.model.request.VideoUploadRequest;
import com.api.streaming.repository.VideoRepository;
import com.api.streaming.service.VideoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpRange;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.FilenameUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class VideoServiceImpl implements VideoService{

    Logger logger = LoggerFactory.getLogger(VideoServiceImpl.class);

    private final Path rootLocation;

    private static final int KB = 1024;
    private static final int MB = 1048576;

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private VideoClasificationService videoClasificationService;

    @Autowired
    public VideoServiceImpl(StorageProperties storageProperties){
        this.rootLocation = Paths.get(storageProperties.getLocation());
    }

    @Override
    public Video storeVideo(VideoUploadRequest request){
        String extension = FilenameUtils.getExtension(request.getVideo().getOriginalFilename());
        if(!extension.equalsIgnoreCase("mp4")){
            throw new IncorrectFileException();
        }
        String videoId = TokenGenerator.generadorTokens();
        storageProcess(request.getVideo(),videoId);
        Video newVideo = createVideoEntity(request.getTitulo(),videoId);
        newVideo = videoRepository.save(newVideo);
        createClasificationEntities(newVideo,request.getClasificaciones());
        return newVideo;
    }

    @Override
    public Pair<UrlResource, ResourceRegion> getVideoAndPartialContent(HttpRange rango, String id) {
        Video newVideoEntity = getVideo(id);
        logger.info(newVideoEntity.getLocation());
        UrlResource videoResource = getUrlResourceFromVideo(newVideoEntity.getLocation());
        ResourceRegion videoRegion = getPartialVideoContent(videoResource,rango);
        return Pair.of(videoResource,videoRegion);
    }

    @Override
    public Video getVideo(String id) {
        return videoRepository.findByIdSerializable(id).get();
    }

    @Override
    public Video deleteVideo(String id) {
        Video videoToEliminate = videoRepository.findByIdSerializable(id).get();
        checkUserAuthorization(getActualSession(),videoToEliminate);
        videoRepository.deleteVideoByIdSerializable(id);
        return videoToEliminate;
    }

    private void checkUserAuthorization(User actualUser,Video video){
        if(!actualUser.getRole().getName().equals("ADMIN") && !actualUser.getId().equals(video.getAutor().getId())){
            throw new AccessDeniedException("El usuario no tiene permitido eliminar este video");
        }
    };

    private ResourceRegion getPartialVideoContent(UrlResource video,HttpRange rangoVideo){
        try {
            long longitudVideo = video.contentLength();
            if(rangoVideo == null) rangoVideo = HttpRange.createByteRange(0,KB);
            long inicioVideo = rangoVideo.getRangeStart(longitudVideo);
            long finVideo = rangoVideo.getRangeEnd(longitudVideo);
            long recorrido = Math.max(KB,finVideo - inicioVideo + 1);
            recorrido = Math.min(MB,recorrido);
            return new ResourceRegion(video,inicioVideo,recorrido);
        }catch(IOException e){
            throw new FailChargeException("fallo al obtener el contenido parcial del video:" + video.getFilename());
        }
    }

    private UrlResource getUrlResourceFromVideo(String Ubicacion){
        try {
            return new UrlResource("file:" + Ubicacion);
        }catch(MalformedURLException e){
            throw new FailChargeException("fallo en get url resource");
        }
    }

    private Video createVideoEntity(String titulo, String videoId){
        Video nuevoVideo = new Video();
        nuevoVideo.setIdSerializable(videoId);
        nuevoVideo.setAutor(userService.getUser(getActualSession().getId()));
        nuevoVideo.setTitulo(titulo);
        nuevoVideo.setLocation(this.rootLocation.toString()+"/" + videoId + ".mp4");
        return nuevoVideo;
    }

    private List<VideoClasification> createClasificationEntities(Video video,ArrayList<Clasification> clasificaciones){
        return videoClasificationService.storeMultipleVideoClasification(video,clasificaciones);
    }

    private void storageProcess(MultipartFile video,String videoId) {
        try {
            String path = videoId + ".mp4";
            Path destinationFile = this.rootLocation.resolve(Paths.get(Objects.requireNonNull(path))).normalize();
            InputStream inputStream = video.getInputStream();
            Files.copy(inputStream,destinationFile,StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new FailChargeException("fallo en storage process");
        }
    }

    private User getActualSession(){
        User actualUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return actualUser;
    }
}
