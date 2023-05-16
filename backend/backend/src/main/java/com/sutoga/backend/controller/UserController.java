package com.sutoga.backend.controller;

import com.sutoga.backend.entity.FriendRequest;
import com.sutoga.backend.entity.User;
import com.sutoga.backend.entity.dto.UserResponse;
import com.sutoga.backend.entity.response.FriendRequestResponse;
import com.sutoga.backend.entity.response.UserSearchResponse;
import com.sutoga.backend.exceptions.ResultNotFoundException;
import com.sutoga.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.sutoga.backend.entity.request.UpdateRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserResponse> getAllUsers(){
        return userService.getAllUsers().stream().map(UserResponse::new).collect(Collectors.toList());
    }

    @GetMapping("/{userId}")
    public UserResponse getOneUser(@PathVariable Long userId) {
        User user = userService.getOneUserById(userId);
        if(user == null) {
            throw new ResultNotFoundException("User with id "+ userId +" not found!" );
        }
        return new UserResponse(user);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Void> updateOneUser(@PathVariable Long userId, @RequestBody UpdateRequest newUser) {
        User user = userService.updateUser(userId, newUser);

        if (user != null) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping("/{userId}")
    public void deleteOneUser(@PathVariable Long userId) {
        userService.deleteById(userId);
    }

    @PostMapping("/saveProfilePhoto/{userId}")
    public ResponseEntity<?> saveProfilePhoto(@RequestParam("file") MultipartFile file, @PathVariable Long userId) {
        return null;
    }

    @PostMapping("/sendFriendRequest")
    public ResponseEntity<Boolean> sendFriendRequest(@RequestParam("senderId") Long senderId, @RequestParam("receiverUsername") String receiverUsername) {
        Boolean isSent = userService.addFriend(senderId, receiverUsername);
        if (Boolean.TRUE.equals(isSent)) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getFriendRecommendations")
    public ResponseEntity<List<String>> getFriendRecommendations(@RequestParam("userId") Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getFriendRecommendationsByUser(userId));
    }

    @GetMapping("/getProfilePhoto/{userId}")
    public ResponseEntity<String> getProfilePhoto(@PathVariable Long userId) {
        return new ResponseEntity<>(userService.getProfilePhotoUrl(userId), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<UserSearchResponse>> searchUsers(@RequestParam("q") String query) {
        List<UserSearchResponse> users = userService.searchUsers(query);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/getByUsername/{username}")
    public ResponseEntity<UserResponse> getUserByUsername(@PathVariable String username) {
        UserResponse user = userService.getUserByUsername(username);
        if (user == null) {
            throw new ResultNotFoundException("User with username " + username + " not found!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @GetMapping("/unconfirmed")
    public ResponseEntity<List<FriendRequest>> getUnconfirmedFriendRequests(@RequestParam("userId") Long userId) {
        List<FriendRequest> unconfirmedRequests = userService.getUnconfirmedFriendRequestsByUserId(userId);
        return new ResponseEntity<>(unconfirmedRequests, HttpStatus.OK);
    }

    @PostMapping("/acceptFriendRequest/{requestId}")
    public ResponseEntity<?> acceptFriendRequest(@PathVariable Long requestId) {
        if (userService.acceptFriendRequest(requestId)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/declineFriendRequest/{requestId}")
    public ResponseEntity<?> declineFriendRequest(@PathVariable Long requestId) {
        if (userService.declineFriendRequest(requestId)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/areFriends")
    public ResponseEntity<Boolean> areFriends(@RequestParam("userId1") Long userId1, @RequestParam("userId2") Long userId2) {
        boolean areFriends = userService.areFriends(userId1, userId2);
        return ResponseEntity.ok(areFriends);
    }

    @PostMapping("/checkFriendRequest")
    public ResponseEntity<FriendRequestResponse> checkFriendRequest(@RequestParam("userId") Long userId,
                                                                    @RequestParam("accountId") Long accountId) {
        FriendRequestResponse friendRequestResponse = userService.checkFriendRequest(userId, accountId);
        return ResponseEntity.ok(friendRequestResponse);
    }

}
