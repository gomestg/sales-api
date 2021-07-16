package io.github.gomestg.rest.controller;

import io.github.gomestg.domain.entity.Product;
import io.github.gomestg.domain.repository.ProductRepository;
import io.swagger.annotations.*;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@Api("API Products")
public class ProductController {

    private ProductRepository repository;

    public ProductController(ProductRepository repository) {
        this.repository = repository;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation("Save a new Product")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Product saved successfully"),
            @ApiResponse(code = 400, message = "Validation Error ")
    })
    public Product save(@RequestBody @Valid Product product) {
        return repository.save(product);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("Delete product by id")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Product deleted successfully"),
            @ApiResponse(code = 404, message = "Product id entered was not found")
    })
    public void delete(@PathVariable Integer id) {
        repository.findById(id)
                .map(product -> {
                    repository.delete(product);
                    return product;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("Update product by id")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Product updated successfully"),
            @ApiResponse(code = 404, message = "Product id entered was not found")
    })
    public void update(@PathVariable Integer id, @RequestBody @Valid Product product) {
        repository
                .findById(id)
                .map(p -> {
                    product.setId(p.getId());
                    repository.save(product);
                    return p;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
    }

    @GetMapping()
    @ApiOperation("Find all products")
    @ApiResponse(code = 200, message = "Find All Products ")
    public List<Product> find(Product filter) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example example = Example.of(filter, matcher);
        return repository.findAll(example);
    }

    @GetMapping("{id}")
    @ApiOperation("Find details of the product by id")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Product FOUND"),
            @ApiResponse(code = 404, message = "Product id entered was not found")
    })
    public Product findProductById(@PathVariable @ApiParam("Product Id") Integer id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
    }

}
