package ssobocik.fp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ssobocik.fp.client.atmosphere.messages.ChatMessage;
import ssobocik.fp.client.rpc.FPService;
import ssobocik.fp.dto.UserDTO;
import ssobocik.fp.exceptions.ObjectNotValidException;
import ssobocik.fp.exceptions.UserNotFoundException;
import ssobocik.fp.server.bl.ChatBl;
import ssobocik.fp.server.bl.UsersBl;
import ssobocik.fp.server.domain.User;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author szymon.sobocik
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "applicationContext-mysqlTests.xml")
public class TestLiveChatBl {

    @Autowired
    private ChatBl chatBl;

    @Autowired
    private UsersBl usersBl;

    @Autowired
    private FPService fpService;

    @Test
    public void testCreateUser() throws ObjectNotValidException {
        List<UserDTO> allUsers = fpService.getAllUsers();
        //there is no users at first
        assertEquals(0, allUsers.size());

        UserDTO newUser = fpService.createNewUser("Adam", "abc");
        checkUserDTO(newUser);

        User byId = usersBl.findById(newUser.getId());
        assertNotNull(byId);
        assertNotNull(byId.getId());
        assertEquals("Adam", byId.getUsername());
        assertEquals("Adam", byId.getNickname());
        assertNotNull(byId.getPassword());
        assertEquals("abc", byId.getPassword());

        allUsers = fpService.getAllUsers();
        assertEquals(1, allUsers.size());
        UserDTO userDTO = allUsers.get(0);
        checkUserDTO(userDTO);
    }

    private void checkUserDTO(UserDTO newUser) {
        assertNotNull(newUser);
        assertNotNull(newUser.getId());
        assertEquals("Adam", newUser.getUsername());
        assertEquals("Adam", newUser.getNickname());
        assertNotNull(newUser.getPassword());
        assertEquals("abc", newUser.getPassword());
    }

    @Test
    public void testSaveEditUser() throws ObjectNotValidException, UserNotFoundException {
        UserDTO newUser = fpService.createNewUser("mark", "mark");
        assertEquals("mark", newUser.getNickname());
        newUser.setNickname("john");
        UserDTO edited = fpService.saveUser(newUser);
        assertEquals("john", edited.getNickname());

        User byId = usersBl.findById(newUser.getId());
        assertEquals("john", byId.getNickname());

        User byUsername = usersBl.findByUsername("mark");
        assertEquals("mark", byUsername.getUsername());
        assertEquals("john", byUsername.getNickname());
    }

    @Test
    public void testRemoveUser() throws UserNotFoundException, ObjectNotValidException {
        List<UserDTO> allUsers = fpService.getAllUsers();
        UserDTO adam = allUsers.get(0);
        assertNotNull(adam);
        assertEquals("Adam", adam.getUsername());

        fpService.removeUser(adam);

        List<UserDTO> allUsersAfterDelete = fpService.getAllUsers();
        assertNotSame(allUsers.size(), allUsersAfterDelete.size());
        assertEquals(allUsers.size() - 1, allUsersAfterDelete.size());
    }

    @Test
    public void testSaveMessage(){
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setMessage("Testing saving");
        chatBl.saveMessage(chatMessage);
    }
}
