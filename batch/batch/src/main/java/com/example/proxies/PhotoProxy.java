package com.example.proxies;

import java.util.List;

import com.example.models.PhotoDTO;

@FeignClient(name="photos", url="https://picsum.photos")
public interface PhotoProxy {
	 @GetMapping(value = "/v2/list?limit=1000")
	 List<PhotoDTO> getAll();
	 @GetMapping("/id/{id}/info")
	 PhotoDTO getOne(@PathVariable int id);
}
