package com.lvyongwenhouzi.architecture.spring.controller;

import com.lvyongwenhouzi.architecture.spring.entity.Girl;
import com.lvyongwenhouzi.architecture.spring.entity.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <b>Restful controller</b>
 *
 * @author 11114396 lvyongwen
 * @date 2020-12-30 15:53
 * @since 1.0
 */
@RestController
@RequestMapping(value = "/girls")
@Slf4j
public class GirlController {

    /**
     * add a girl
     *
     * @return
     */
    @PostMapping
    public Result<Object> girlAdd(@RequestBody Girl girl, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return Result.fail("-1", "失败");
        }

        return Result.success(girl);
    }
}
