package net.yafw.forum.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BaseModel<T> {

	 private boolean isSuccess;
	 private List<String> errorMessages;
	 private T data;
	 private String timeStamp;
}
