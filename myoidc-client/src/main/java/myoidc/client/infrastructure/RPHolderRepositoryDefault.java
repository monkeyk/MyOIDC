package myoidc.client.infrastructure;

import myoidc.client.domain.RPHolder;
import myoidc.client.domain.RPHolderRepository;
import org.apache.commons.lang3.StringUtils;
import org.jose4j.json.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

/**
 * 2020/4/7
 * <p>
 * 默认存储 RPHolder 在临时文件中/
 * <p>
 * 实际使用时用具体的实现（如存数据库）
 *
 * @author Shengzhao Li
 * @since 1.1.0
 */
@Repository
public class RPHolderRepositoryDefault implements RPHolderRepository {

    private static final Logger LOG = LoggerFactory.getLogger(RPHolderRepositoryDefault.class);

    //文件名
    private static final String FILE = "myoidc-client-rpholder.json";


    //临时文件对象
    private File file;

    @Override
    public RPHolder findRPHolder() {
        try {
            File file = getFile();
            String json = FileCopyUtils.copyToString(new FileReader(file));
            if(StringUtils.isNotBlank(json)){
                Map<String, Object> map = JsonUtil.parseJson(json);
                return new RPHolder(map);
            }
        } catch (Exception e) {
            LOG.error("File handle error", e);
        }
        //返回一空对象
        return new RPHolder();
    }

    @Override
    public boolean saveRPHolder(RPHolder rpHolder) {
        try {
            File file = getFile();
            String json = rpHolder.toJSON();
            FileCopyUtils.copy(json, new FileWriter(file));
            return true;
        } catch (IOException e) {
            LOG.error("File handle error", e);
        }
        return false;
    }

    private File getFile() throws IOException {
        if (file != null && file.exists()) {
            LOG.debug("Use cached file: {}", file);
            return file;
        }
        file = new File(FILE);
        if (!file.exists()) {
            boolean ok = file.createNewFile();
            LOG.debug("Create file: {} result: {}", FILE, ok);
        }
        return file;
    }
}
