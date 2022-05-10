package ru.job4j.dreamjob.control;

import org.junit.Test;
import org.springframework.ui.Model;
import ru.job4j.dreamjob.model.City;
import ru.job4j.dreamjob.model.Post;
import ru.job4j.dreamjob.service.CityService;
import ru.job4j.dreamjob.service.PostService;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;

public class PostControllerTest {

    @Test
    public void whenPosts() {
        List<Post> posts = Arrays.asList(
                new Post(1, "New post", "text", "today", new City()),
                new Post(2, "New post", "text", "today", new City())
        );
        List<City> cities = Arrays.asList(
                new City(1, "Москва"),
                new City(2, "Санкт-Петербург")
        );
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        when(postService.findAll()).thenReturn(posts);
        when(cityService.getAlCities()).thenReturn(cities);
        PostController postController = new PostController(postService, cityService);
        String page = postController.posts(model, session);
        verify(model).addAttribute("posts", posts);
        verify(model).addAttribute("cities", cities);
        assertThat(page, is("posts"));
    }

    @Test
    public void whenCreatePost() {
        Post input = new Post(1, "New post", "text", "today", new City());
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        PostController postController = new PostController(postService, cityService);
        String page = postController.createPost(input);
        verify(postService).add(input);
        assertThat(page, is("redirect:/posts"));
    }

    @Test
    public void whenUpdatePost() {
        Post input = new Post(1, "New post", "text", "today", new City());
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        PostController postController = new PostController(postService, cityService);
        String page = postController.updatePost(input);
        verify(postService).update(input);
        assertThat(page, is("redirect:/posts"));
    }

    @Test
    public void whenFromAddPost() {
        List<City> cities = Arrays.asList(
                new City(1, "Москва"),
                new City(2, "Санкт-Петербург")
        );
        Model model = mock(Model.class);
        HttpSession session = mock(HttpSession.class);
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        when(cityService.getAlCities()).thenReturn(cities);
        PostController postController = new PostController(postService, cityService);
        String page = postController.addPost(model, session);
        verify(model).addAttribute("cities", cities);
        assertThat(page, is("addPost"));
    }

    @Test
    public void whenFromUpdatePost() {
        Post post = new Post(1, "New post", "text", "today", new City());
        List<City> cities = Arrays.asList(
                new City(1, "Москва"),
                new City(2, "Санкт-Петербург")
        );
        Model model = mock(Model.class);
        PostService postService = mock(PostService.class);
        CityService cityService = mock(CityService.class);
        when(postService.findById(1)).thenReturn(post);
        when(cityService.getAlCities()).thenReturn(cities);
        PostController postController = new PostController(postService, cityService);
        String page = postController.formUpdatePost(model, post.getId());
        verify(model).addAttribute("post", post);
        verify(model).addAttribute("cities", cities);
        assertThat(page, is("updatePost"));
    }
}