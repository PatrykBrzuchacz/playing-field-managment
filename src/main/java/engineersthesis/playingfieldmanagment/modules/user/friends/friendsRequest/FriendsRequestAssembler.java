package engineersthesis.playingfieldmanagment.modules.user.friends.friendsRequest;

import engineersthesis.playingfieldmanagment.web.dto.FriendsRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FriendsRequestAssembler {

    public Page<FriendsRequestDto> toDtoList(Page<FriendsRequest> allByReceiverAndRequestStatus, Pageable pageable) {
        return new PageImpl<>(allByReceiverAndRequestStatus.stream().map(this::toDto).collect(Collectors.toList()),
                pageable, allByReceiverAndRequestStatus.getTotalElements());
    }

    private FriendsRequestDto toDto(FriendsRequest friendsRequest) {
        return new FriendsRequestDto(friendsRequest.getId(), friendsRequest.getSender().getUsername(),
                friendsRequest.getRequestStatus());
    }

    public List<String> toSendedRequestsDtoList(List<FriendsRequest> friendsRequests) {
        return friendsRequests.stream().map(this::toSendedRequestsDto).collect(Collectors.toList());
    }

    private String toSendedRequestsDto(FriendsRequest friendsRequest) {
        return friendsRequest.getReceiver().getUsername();
    }
}
