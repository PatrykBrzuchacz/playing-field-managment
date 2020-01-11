package engineersthesis.playingfieldmanagment.web.controller;

import engineersthesis.playingfieldmanagment.modules.user.conversation.Conversation;
import engineersthesis.playingfieldmanagment.modules.user.conversation.ConversationService;
import engineersthesis.playingfieldmanagment.modules.user.conversation.message.MessageService;
import engineersthesis.playingfieldmanagment.web.dto.ConversationDto;
import engineersthesis.playingfieldmanagment.web.dto.MessageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
    @Autowired
    private MessageService messageService;
    @Autowired
    private ConversationService conversationService;
    @PostMapping("/{senderId}/sendMessage")
    private MessageDto sendMessage(@PathVariable("senderId") Long id, @RequestParam("content") String content) {
        return messageService.sendMessage(id, content);
    }

    @GetMapping("/{senderId}")
    private List<MessageDto> getMessages(@PathVariable("senderId") Long id) {
        return messageService.getMessages(id);
    }

    @GetMapping()
    private Page<ConversationDto> getConversation(Pageable pageable) {
        return conversationService.getConversations(pageable);
    }
}
