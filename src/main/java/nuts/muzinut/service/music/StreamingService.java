package nuts.muzinut.service.music;

import lombok.RequiredArgsConstructor;
import nuts.muzinut.exception.NotFoundEntityException;
import nuts.muzinut.exception.NotFoundFileException;
import nuts.muzinut.repository.music.SongRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class StreamingService {
    private final SongRepository songRepository;
    @Value("${spring.file.dir}")
    private String fileDir;
    public UrlResource streamingSong(Long songId) {
        // 해당하는 songId 가 존재하지 않는 경우 Exception 출력
        if(songRepository.findById(songId).isEmpty()) throw new NotFoundEntityException("요청하신 songId(" + songId + ") 에 해당하는 Entity가 존재하지 않습니다.");

        String songName = songRepository.findById(songId).get().getFileName();
        Path songPath = Paths.get(fileDir + "/songFile/" + songName);
        UrlResource resource;
        try {
            resource = new UrlResource(songPath.toUri());
            // 해당하는 경로에 파일이 존재하지 않는 경우
            if(!resource.isReadable()) throw new NotFoundFileException(songName + " 파일이 존재하지 않습니다.");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        return resource;
    }
}
