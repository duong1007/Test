package c1020g1.social_network.controller;

import c1020g1.social_network.model.FriendRequest;
import c1020g1.social_network.model.User;
import c1020g1.social_network.service.UserService;
import c1020g1.social_network.service.friend_request_service.FriendRequestService;
import c1020g1.social_network.service.friends_service.FriendsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class FriendRequestController {

    @Autowired
    FriendRequestService friendRequestService;

    @Autowired
    UserService userService;

    @Autowired
    FriendsService friendsService;

    /**
     * Author : TungNT
     * Get All Friend Request Of User
     */
    @GetMapping("/friend_request/{id}")
    public ResponseEntity<List<FriendRequest>> getAllFriendRequest(@PathVariable Integer id) {

        try {
            List<FriendRequest> friendRequestList = friendRequestService.findAllFriendRequest(id);

            for (int i = 0; i < friendRequestList.size(); i++) {
                FriendRequest friendRequest = friendRequestList.get(i);

                List<User> mutualFriend = friendsService.findMutualFriend
                        (friendRequest.getReceiveUser().getUserId(), friendRequest.getSendUser().getUserId());

                friendRequestList.get(i).setMutualFriends(mutualFriend);
            }
            return new ResponseEntity<>(friendRequestList, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Author: TungNT
     * Create Friend Request from user A to user B
     */
    @PostMapping("/friend_request")
    public ResponseEntity<Void> createFriendRequest(@RequestBody FriendRequest friendRequest) {
        try {
            if (userService.getUserById(friendRequest.getSendUser().getUserId()) == null ||
                    userService.getUserById(friendRequest.getReceiveUser().getUserId()) == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            if (friendRequestService.saveFriendRequest(friendRequest).equals("NG")) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Author : TungNT
     * Delete friend request
     */
    @DeleteMapping("/delete/friend_request/{id}")
    public ResponseEntity<Void> deleteFriendRequest(@PathVariable Integer id) {

        try {
            if (friendRequestService.deleteFriendRequest(id).equals("NG")) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Author: TungNT
     * Get Mutual Friend between send user and receive user.
     */
    @GetMapping("/mutual/{id1}/{id2}")
    public ResponseEntity<List<User>> getMutual(@PathVariable Integer id1,@PathVariable Integer id2){
        try {
            List<User> mutual = friendsService.findMutualFriend(id1, id2);
            return new ResponseEntity<>(mutual, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
