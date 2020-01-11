package engineersthesis.playingfieldmanagment.modules.user.conversation;

import engineersthesis.playingfieldmanagment.modules.security.model.User;
import engineersthesis.playingfieldmanagment.modules.security.service.SecurityUserHelper;
import engineersthesis.playingfieldmanagment.modules.user.conversation.message.Message;
import engineersthesis.playingfieldmanagment.web.dto.ConversationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConversationService {

    @Autowired
    private ConversationRepository conversationRepository;
    @Autowired
    private SecurityUserHelper securityUserHelper;

    public Page<ConversationDto> getConversations(Pageable pageable) {
        User loggedUser = securityUserHelper.getLoggedUser();
        Page<Conversation> conversations = conversationRepository.findAllByUserOneOrUserTwo(loggedUser,loggedUser,
                pageable);

        return toDtoList(conversations,loggedUser,pageable);
    }

private Page<ConversationDto> toDtoList(Page<Conversation> conversations, User loggedUser,Pageable pageable) {
        return new PageImpl<>(conversations.stream().map(val-> toDto(val, loggedUser)).collect(Collectors.toList()),
                pageable,conversations.getTotalElements());
    }
    private ConversationDto toDto(Conversation conversation, User loggedUser) {
        if(conversation.getUserOne()==loggedUser){
            return new ConversationDto(conversation.getUserTwo().getUsername(), conversation.getUserTwo().getId(),
                    conversation.getLastMessage());
        } else {
            return new ConversationDto(conversation.getUserOne().getUsername(), conversation.getUserOne().getId(),
                    conversation.getLastMessage());
        }
    }
}
