package cn.rzpt.user.domain.service.impl;

import cn.rzpt.user.domain.service.UserDomainService;
import cn.rzpt.user.infrastructure.repository.po.User;
import cn.rzpt.user.interfaces.UserInterface;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDomainServiceImpl implements UserDomainService {

    private final UserInterface userInterface;



}
