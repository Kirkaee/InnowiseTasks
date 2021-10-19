package by.innowise.tasks.mapper;

import by.innowise.tasks.dto.SpeakerDto;
import by.innowise.tasks.entity.Speaker;
import org.springframework.stereotype.Component;

@Component
public class SpeakerMapper {

    public SpeakerDto toSpeakerDto(Speaker speaker) {
        return SpeakerDto.builder()
                .id(speaker.getId())
                .fio(speaker.getFio())
                .title(speaker.getTitle())
                .membership(speaker.getMembership())
                .build();
    }

    public Speaker toSpeaker(SpeakerDto speakerDto) {
        return Speaker.builder()
                .id(speakerDto.getId())
                .fio(speakerDto.getFio())
                .title(speakerDto.getTitle())
                .membership(speakerDto.getMembership())
                .build();
    }
}
