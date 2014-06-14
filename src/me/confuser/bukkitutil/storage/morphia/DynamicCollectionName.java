package me.confuser.bukkitutil.storage.morphia;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Map;

import org.mongodb.morphia.annotations.CappedAt;
import org.mongodb.morphia.annotations.Entity;

public class DynamicCollectionName {

	@SuppressWarnings("unchecked")
	public DynamicCollectionName(final String name, Class<?> entityClass) {
		// Set the collection prefix
		final Entity oldAnnotation = (Entity) entityClass.getAnnotations()[0];

		Annotation newAnnotation = new Entity() {

			@Override
			public String value() {
				return name;
			}

			@Override
			public Class<? extends Annotation> annotationType() {
				return oldAnnotation.annotationType();
			}

			@Override
			public CappedAt cap() {
				return oldAnnotation.cap();
			}

			@Override
			public boolean noClassnameStored() {
				return oldAnnotation.noClassnameStored();
			}

			@Override
			public boolean queryNonPrimary() {
				return oldAnnotation.queryNonPrimary();
			}

			@Override
			public String concern() {
				return oldAnnotation.concern();
			}
		};

		Field field = null;

		try {
			field = Class.class.getDeclaredField("annotations");
		} catch (NoSuchFieldException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		field.setAccessible(true);

		Map<Class<? extends Annotation>, Annotation> annotations = null;

		try {
			annotations = (Map<Class<? extends Annotation>, Annotation>) field.get(entityClass);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		annotations.put(Entity.class, newAnnotation);

	}
}
