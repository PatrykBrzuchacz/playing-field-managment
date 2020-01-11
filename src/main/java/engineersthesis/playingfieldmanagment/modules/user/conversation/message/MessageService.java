package engineersthesis.playingfieldmanagment.modules.user.conversation.message;

import engineersthesis.playingfieldmanagment.modules.infrastructure.notifications.Notification;
import engineersthesis.playingfieldmanagment.modules.infrastructure.notifications.NotificationService;
import engineersthesis.playingfieldmanagment.modules.security.model.User;
import engineersthesis.playingfieldmanagment.modules.security.repository.UserRepository;
import engineersthesis.playingfieldmanagment.modules.security.service.SecurityUserHelper;
import engineersthesis.playingfieldmanagment.modules.user.conversation.Conversation;
import engineersthesis.playingfieldmanagment.modules.user.conversation.ConversationRepository;
import engineersthesis.playingfieldmanagment.web.dto.ConversationDto;
import engineersthesis.playingfieldmanagment.web.dto.MessageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SecurityUserHelper securityUserHelper;
    @Autowired
    private MessageAssembler messageAssembler;
    @Autowired
    private ConversationRepository conversationRepository;
    @Autowired
    private NotificationService notificationService;
    @Transactional
    public MessageDto sendMessage(Long id, String content) {
        User loggedUser = securityUserHelper.getLoggedUser();
        Conversation conversation = conversationRepository.findByUserOne_IdAndUserTwo_Id(id,
                loggedUser.getId());
        if(conversation==null) {
            conversation = conversationRepository.findByUserOne_IdAndUserTwo_Id(loggedUser.getId(), id);
        }

        if(conversation!=null) {
            conversation.setLastMessage(LocalDateTime.now());
        } else {
            conversation = conversationRepository.save(new Conversation(loggedUser, userRepository.getOne(id),
                    LocalDateTime.now()));
        }
        User user = userRepository.getOne(id);
        notificationService.saveConversationNotification(conversation,user,loggedUser);
        Message message = messageRepository.save(new Message(loggedUser,
                userRepository.getOne(id), content,
                LocalDateTime.now(), conversation));

        return messageAssembler.toDto(message);
    }

    public List<MessageDto> getMessages(Long id) {
        User loggedUser = securityUserHelper.getLoggedUser();
        Conversation conversations  = conversationRepository.findByUserOne_IdAndUserTwo_Id(loggedUser.getId(), id);
        if(conversations==null) {
            conversations =  conversationRepository.findByUserOne_IdAndUserTwo_Id(id, loggedUser.getId());
        }
        List<Message> messages = new ArrayList<>();

        if(conversations!=null && !conversations.getMessages().isEmpty()) {
            Collections.sort(conversations.getMessages());
            messages = conversations.getMessages();
        }

        return messageAssembler.toDtoList(messages);
    }}



