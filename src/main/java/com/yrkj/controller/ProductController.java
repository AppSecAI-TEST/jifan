package com.yrkj.controller;

import com.yrkj.model.core.*;
import com.yrkj.model.product.Product;
import com.yrkj.model.product.ProductSearch;
import com.yrkj.service.ProductService;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by xuenianxiang on 2017/6/18.
 */

@RestController
@RequestMapping("/api/product")
@EnableSwagger2
@Api(description = "商品管理接口")
public class ProductController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private ProductService productService;

    @ApiOperation(value = "创建产品",notes = "创建产品")
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "Bearer {token}", required = true, dataType = "String",paramType = "header")})
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ActionResult add(@RequestBody Product product) {

        Claims claims = (Claims)request.getAttribute("claims");

        product.setCreate_by(claims.getSubject());
        product.setCreate_time(new Date());

        return productService.add(product);
    }

    @ApiOperation(value = "更新产品",notes = "更新产品")
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "Bearer {token}", required = true, dataType = "String",paramType = "header")})
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ActionResult update(@RequestBody Product product) {

        Claims claims = (Claims)request.getAttribute("claims");

        product.setUpdate_by(claims.getSubject());
        product.setUpdate_time(new Date());

        return productService.update(product);
    }

    @ApiOperation(value = "批量删除产品",notes = "批量删除产品")
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "Bearer {token}", required = true, dataType = "String",paramType = "header")})
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ActionResult delete(@RequestBody IdsInput input) {

        Claims claims = (Claims)request.getAttribute("claims");

        IdsModel model = new IdsModel();

        model.setList(input.getList());
        model.setUpdate_by(claims.getSubject());
        model.setUpdate_time(new Date());

        return productService.deleteProducts(model);
    }

    @ApiOperation(value = "批量更改状态",notes = "批量更改状态")
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "Bearer {token}", required = true, dataType = "String",paramType = "header")})
    @RequestMapping(value = "/updateStatus", method = RequestMethod.POST)
    public ActionResult updateStatus(@RequestBody ChangeStatusInput input) {

        Claims claims = (Claims)request.getAttribute("claims");

        ChangeStatusModel model = new ChangeStatusModel();

        model.setStatus(input.getStatus());
        model.setList(input.getList());
        model.setUpdate_by(claims.getSubject());
        model.setUpdate_time(new Date());

        return productService.updateStatus(model);
    }

    @ApiOperation(value = "获取产品详情",notes = "获取产品详情")
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "Bearer {token}", required = true, dataType = "String",paramType = "header")})
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public ActionResult get(@RequestParam Long id) throws ServletException {
        return productService.getById(id);
    }

    @ApiOperation(value = "获取产品列表",notes = "获取产品列表")
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "Bearer {token}", required = true, dataType = "String",paramType = "header")})
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public PageModel list(@RequestBody ProductSearch model) {
        return productService.getAll(model);
    }

    @ApiOperation(value = "获取单一产品列表",notes = "获取单一产品列表")
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "Bearer {token}", required = true, dataType = "String",paramType = "header")})
    @RequestMapping(value = "/singleList", method = RequestMethod.POST)
    public PageModel singleList(@RequestBody ProductSearch model) {
        return productService.getAllSingle(model);
    }

    @ApiOperation(value = "获取勋章商品列表",notes = "获取勋章商品列表")
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "Bearer {token}", required = true, dataType = "String",paramType = "header")})
    @RequestMapping(value = "/badgeList", method = RequestMethod.POST)
    public PageModel badgeList(@RequestBody ProductSearch model) {
        return productService.getBadgeList(model);
    }
}
