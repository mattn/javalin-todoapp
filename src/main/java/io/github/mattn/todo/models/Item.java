package io.github.mattn.todo.models;

import java.util.Date;
import lombok.Data;

/**
 * Item.
 */
@Data
public class Item {
	private int	id;
	private String body;
	private boolean done;
	private Date createdAt;
	private Date updatedAt;
}
