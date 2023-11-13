package com.greglturnquist.learningspringboot.reactiveweb;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.FileSystemUtils;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/*
 * @Service: This indicates this is a Spring bean used as a service. Spring Boot will automatically scan this class and create an instance. 
 * UPLOAD_ROOT: This is the base folder where images will be stored. 
 * ResourceLoader: This is a Spring utility class used to manage files. 
 *      It is created automatically by Spring Boot and injected to our service via constructor injection. 
 *      This ensures our service starts off with a consistent state.
 */
@Service
public class ImageService {
    private static String UPLOAD_ROOT = "upload-dir";
    private final ResourceLoader resourceLoader;

    public ImageService(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    /*
     * This method returns Flux<Image>, a container of images that only gets created when the consumer subscribes. 
     * The Java NIO APIs are used to create a Path from UPLOAD_ROOT, which is used to open a lazy DirectoryStream courtesy of Files.newDirectoryStream(). 
     * DirectoryStream is a lazy iterable, which means that nothing is fetched until next() is called, making it a perfect fit for our Reactor Flux. 
     * Flux.fromIterable is used to wrap this lazy iterable, allowing us to only pull each item as demanded by a reactive streams client. 
     * The Flux maps over the paths, converting each one to an Image. 
     * In the event of an exception, an empty Flux is returned. 
     */
    public Flux<Image> findAllImages() {
        try {
            return Flux.fromIterable(Files.newDirectoryStream(Paths.get(UPLOAD_ROOT)))
            .map(path ->
                new Image(
                    String.valueOf(path.hashCode()),
                    path.getFileName().toString()));
        } catch (IOException e) {
            return Flux.empty();
        }
    }

    /*
     * Since this method only handles one image, it returns a Mono<Resource>. 
     *      Remember, Mono is a container of one. Resource is Spring's abstract type for files. 
     * resourceLoader.getResource() fetches the file based on filename and UPLOAD_ROOT. 
     * To delay fetching the file until the client subscribes, we wrap it with Mono.fromSupplier(), and put getResource() inside a lambda. 
     */
    public Mono<Resource> findOneImage(String filename) {
        return Mono.fromSupplier(() -> 
            resourceLoader.getResource(
                "file:" + UPLOAD_ROOT + "/" + filename)
            );
    }

    /*
     * This method returns a Mono<Void> indicating that it has no resulting value, 
     *      but we still need a handle in order to subscribe for this operation to take place 
     * The incoming Flux of FilePart objects are flatMapped over, so we can process each one 
     * Each file is tested to ensure it's not empty 
     * At the heart of our chunk of code, Spring Framework 5's FilePart transfers the content into a new file stored in UPLOAD_ROOT 
     * then() lets us wait for the entire Flux to finish, yielding a Mono<Void>
     */
    public Mono<Void> createImage(Flux<FilePart> files) {
        return files.flatMap(file -> file.transferTo(
            Paths.get(UPLOAD_ROOT, file.filename()).toFile()
        )).then();
    }

    /*
     * Because this method doesn't care about return values, its return type is Mono<Void>. 
     * To hold off until subscribe, we need to wrap our code with Mono.fromRunnable(), 
     *      and use a lambda expression to coerce a Runnable. This lets us put our code off to the side until we're ready to run it. 
     * Inside all of that, we can use Java NIO's handy Files.deleteIfExists().
     */
    public Mono<Void> deleateImage(String filename) {
        return Mono.fromRunnable(() -> {
            try {
                Files.deleteIfExists(Paths.get(UPLOAD_ROOT, filename));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /*
     * @Bean indicates that this method will return back an object to be registered as a Spring bean at the time that ImageService is created. 
     * The bean returned is a CommandLineRunner. 
     * Spring Boot runs ALL CommandLineRunners after the application context is fully realized (but not in any particular order). 
     * This method uses a Java 8 lambda, which gets automatically converted into a CommandLineRunner via Java 8 SAM (Single Abstract Method) rules. 
     * The method deletes the UPLOAD_ROOT directory, creates a new one, then creates three new files with a little bit of text. 
     */

    /**
     * * Pre-load some test images * * @return Spring Boot {@link CommandLineRunner}
     * automatically * run after app context is loaded.
     */
    @Bean
    CommandLineRunner setUp() throws IOException {
        return (args) -> {
            FileSystemUtils.deleteRecursively(new File(UPLOAD_ROOT));

            Files.createDirectory(Paths.get(UPLOAD_ROOT));

            FileCopyUtils.copy("Test file", new FileWriter(UPLOAD_ROOT + "/learning-spring-boot-cover.jpg"));
            FileCopyUtils.copy("Test file2", new FileWriter(UPLOAD_ROOT + "/learning-spring-boot-2nd-edition-cover.jpg"));
            FileCopyUtils.copy("Test file3", new FileWriter(UPLOAD_ROOT + "/bazinga.jpg"));
        };
    }
}