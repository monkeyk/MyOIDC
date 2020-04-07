package myoidc.client.service.impl;

import myoidc.client.domain.RPHolder;
import myoidc.client.domain.RPHolderRepository;
import myoidc.client.service.MyOIDCClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 2020/4/7
 *
 * @author Shengzhao Li
 * @since 1.1.0
 */
@Service
public class MyOIDCClientServiceImpl implements MyOIDCClientService {


    @Autowired
    private RPHolderRepository rpHolderRepository;

    @Override
    public RPHolder loadRPHolder() {
        return rpHolderRepository.findRPHolder();
    }

    @Override
    public boolean saveRPHolder(RPHolder rpHolder) {
        return rpHolderRepository.saveRPHolder(rpHolder);
    }
}
