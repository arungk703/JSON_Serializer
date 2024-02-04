package main;

import java.lang.reflect.Array;
import java.lang.reflect.Field;

import data.Address;
import data.Person;

public class JSONSerializer {

	public static void main(String[] args) throws IllegalAccessException {

		Address address = new Address("1-2-3", "Madhapur", "HYD", "TS", "INDIA");
		Person person = new Person("Arun", "8988983832", "arungk703@gmail.com", 23, 25000.00f, false, address,
				new String[] { "Cycle", "Bike" });
		String json = objectToJson(person);

		System.out.println(json);
	}

	public static String objectToJson(Object instance) throws IllegalAccessException {

		StringBuilder json = new StringBuilder();
		Field fields[] = instance.getClass().getDeclaredFields();
		json.append("{");

		for (int i = 0; i < fields.length; i++) {
			fields[i].setAccessible(true);

			if (fields[i].isSynthetic()) // This fields are created by compiler.
				continue;

			json.append(formatStringValue(fields[i].getName()));
			json.append(":");

			if (fields[i].getType().isPrimitive())
				json.append(formatPrimitiveValue(fields[i].get(instance), fields[i].getType()));
			else if (fields[i].getType().equals(String.class))
				json.append(formatStringValue(fields[i].get(instance).toString()));
			else if (fields[i].getType().isArray())
				json.append(arrayToJsonObject(fields[i].get(instance)));
			else
				json.append(objectToJson(fields[i].get(instance)));

			if (i != fields.length - 1)
				json.append(", ");
		}

		json.append("}");

		return json.toString();

	}

	public static String formatStringValue(String value) {
		return String.format("\"%s\"", value);
	}

	public static String formatPrimitiveValue(Object instance, Class<?> type) throws IllegalAccessException {

		if (type.equals(boolean.class) || type.equals(int.class) || type.equals(long.class) || type.equals(short.class)
				|| type.equals(byte.class))
			return instance.toString();

		else if (type.equals(float.class) || type.equals(double.class))
			return String.format("%.2f", instance);

		else if (type.equals(char.class))
			return String.format("'%s'", instance.toString());

		throw new RuntimeException(String.format("Type: %s is not supported.", type.getName()));

	}

	public static String arrayToJsonObject(Object arrayInstance) throws IllegalAccessException {

		StringBuilder arrayJson = new StringBuilder();

		int length = Array.getLength(arrayInstance);

		Class<?> componentType = arrayInstance.getClass().getComponentType();

		arrayJson.append("[");
		for (int i = 0; i < length; i++) {

			Object element = Array.get(arrayInstance, i);

			if (componentType.isPrimitive())
				arrayJson.append(formatPrimitiveValue(element, componentType));
			else if (componentType.equals(String.class))
				arrayJson.append(formatStringValue(element.toString()));
			else
				arrayJson.append(objectToJson(element));

			if (i != length - 1)
				arrayJson.append(", ");
		}

		arrayJson.append("]");

		return arrayJson.toString();

	}

}
