package benblack86.java;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Reification {

	public static <T> List<T> asList(final Collection<T> collection) {
		// cannot do List<T> as information is erased
		if (collection instanceof List<?>) {
			// can cast using T as the argument expects T
			return (List<T>) collection;
		} else {
			throw new IllegalArgumentException("Argument not a list");
		}
	}

	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		final Collection<String> collection = Arrays.asList("hi", "there");
		final List<String> list = Reification.asList(collection);
		System.out.println(list);

		final List<Object> objects = Arrays.<Object> asList("three", "four");
		final List<String> strings = Reification.promote(objects);
		System.out.println(strings);

		final String[] stringArray = Reification.toArray(strings, new String[0]);
		System.out.println(Arrays.toString(stringArray));

		final String[] stringArray2 = Reification.toArray(strings, String.class);
		System.out.println(Arrays.toString(stringArray2));

		final List<String> strins = new ArrayList<String>() { // Type Erasure
																// will not be
																// performed
			@Override
			public void clear() {
				// TODO Auto-generated method stub
				super.clear();
			}
		};
		System.out.println(strins);
	}

	@SuppressWarnings("unchecked")
	public static List<String> promote(final List<Object> objs) {
		for (final Object obj : objs) {
			if (!(obj instanceof String)) {
				throw new ClassCastException();
			}
		}

		return (List<String>) (List<?>) objs; // unchecked cast
	}

	@SuppressWarnings("unchecked")
	public static <T> T[] toArray(final Collection<T> collection, final Class<T> c) {
		final T[] a = (T[]) Array.newInstance(c, collection.size()); // unchecked
																		// cast

		int i = 0;
		for (final T element : collection) {
			a[i++] = element;
		}

		return a;
	}

	@SuppressWarnings("unchecked")
	public static <T> T[] toArray(final Collection<T> collection, T[] a) {
		if (a.length < collection.size()) {
			a = (T[]) Array.newInstance(a.getClass().getComponentType(), collection.size()); // unchecked
																								// cast
		}

		int i = 0;
		for (final T element : collection) {
			a[i++] = element;
		}

		return a;
	}
}
