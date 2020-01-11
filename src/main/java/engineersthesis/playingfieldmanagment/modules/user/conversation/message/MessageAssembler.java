package engineersthesis.playingfieldmanagment.modules.user.conversation.message;

import engineersthesis.playingfieldmanagment.web.dto.MessageDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageAssembler {

    public MessageDto toDto(Message message) {
        return new MessageDto(message.getSended(), message.getSender().getId(), message.getContent());
    }

    public List<MessageDto> toDtoList(List<Message> messages) {
        return messages.stream().map(this::toDto).collect(Collectors.toList());
    }
}
