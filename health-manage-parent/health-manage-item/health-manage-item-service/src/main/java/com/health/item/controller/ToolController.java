package com.health.item.controller;

import com.health.item.pojo.Tool;
import com.health.item.service.IToolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created with IDEA
 *
 * @author chong  liu
 * @create 2020/4/18 8:46
 */
@Controller
@RequestMapping("/tool")
public class ToolController {
    @Autowired
    private IToolService toolService;

    @GetMapping("/cards")
    public ResponseEntity<List<Tool>> getCards(){
        List<Tool> tools =  this.toolService.getTools();
        if(CollectionUtils.isEmpty(tools)){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tools);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tool> getToolById(@PathVariable(name = "id")Integer id){
        Tool tool = this.toolService.getToolById(id);
        if(tool == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tool);
    }


}
