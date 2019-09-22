package io.github.mattn.todo;

import io.github.mattn.todo.models.Item;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import io.javalin.plugin.rendering.vue.VueComponent;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

/**
 * Main.
 */
public class Main {
	/**
	 * Main function.
	 * @param args arguments for program.
	 * @throws Exception throws when something wrong.
	 */
	public static void main(String[] args) throws Exception {
		SqlSessionFactory factory;
		try (InputStream in = Main.class.getResourceAsStream("/mybatis-config.xml")) {
			factory = new SqlSessionFactoryBuilder().build(in);
		}

		Javalin app = Javalin.create().start(7000);
		app.config
			.addStaticFiles("src/main/resources/public", Location.EXTERNAL)
			.enableWebjars();
		app.get("/api/todo", ctx -> {
			ctx.res.setContentType("text/json");

			try (SqlSession session = factory.openSession()) {
				List<Item> result = session.selectList("io.github.mattn.todo.select");
				ctx.json(result);
			}
		});
		app.post("/api/todo", ctx -> {
			Item item = ctx.bodyAsClass(Item.class);
			try (SqlSession session = factory.openSession()) {
				session.insert("io.github.mattn.todo.insert", item);
				session.commit();
				ctx.status(201);
			}
		});
		app.post("/api/todo/:id", ctx -> {
			Item item = ctx.bodyAsClass(Item.class);
			item.setUpdatedAt(new Date());
			try (SqlSession session = factory.openSession()) {
				session.insert("io.github.mattn.todo.update", item);
				session.commit();
			}
		});
		app.get("/", new VueComponent("<todo-overview></todo-overview>"));
	}
}
