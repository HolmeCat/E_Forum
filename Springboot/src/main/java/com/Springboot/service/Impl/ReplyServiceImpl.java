package com.Springboot.service.Impl;

import com.Springboot.repository.ReplyRepository;
import com.Springboot.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ReplyServiceImpl implements ReplyService {
    @Autowired
    private ReplyRepository replyRepository;

}
