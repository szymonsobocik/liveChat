package ssobocik.fp.server.security;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ssobocik.fp.dto.UserDTO;
import ssobocik.fp.exceptions.ObjectNotValidException;
import ssobocik.fp.server.bl.UsersBl;

import java.lang.reflect.Method;

/**
 * Optimictic locking
 * Checks if user which is to be edited is in current version,
 * for example if other user modyfied it.
 *
 * @author szymon.sobocik
 */
@Service("interceptorVersionUser")
public class InterceptorVersionUser implements MethodInterceptor {

    @Autowired
    private UsersBl usersBl;

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Method method = invocation.getMethod();
        if (method != null) {
            CheckVersionUser annotation = method.getAnnotation(CheckVersionUser.class);
            if (annotation != null) {
                Object[] arguments = invocation.getArguments();
                if (arguments.length == 1 && arguments[0] instanceof UserDTO) {
                    UserDTO userDTO = (UserDTO) arguments[0];
                    if (userDTO.getId() != null) {
                        //It is not a new user
                        usersBl.checkUserInVersion(userDTO.getId(), userDTO.getVersion());
                    }
                } else {
                    throw new ObjectNotValidException("Method with version control should take UserDTO as an argument");
                }
            }
        }
        return invocation.proceed();
    }


}