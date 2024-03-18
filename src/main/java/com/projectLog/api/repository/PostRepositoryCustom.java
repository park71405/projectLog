package com.projectLog.api.repository;

import com.projectLog.api.domain.Post;
import com.projectLog.api.request.PostSearch;

import java.util.List;

public interface PostRepositoryCustom {

    List<Post> getList(PostSearch postSearch);

}
