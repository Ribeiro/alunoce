package br.com.curso.adapter;

import java.lang.reflect.Field;
import java.util.ArrayList;

import roboguice.inject.InjectView;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * @author Ildar Karimov
 */
public abstract class RoboBaseAdapter extends BaseAdapter {
	protected ArrayList<ViewMembersInjector> viewsForInjection = new ArrayList<ViewMembersInjector>();
	private Context context;
	private int layoutId;
	private LayoutInflater inflater;

	private void init() {
		inflater = LayoutInflater.from(context);
		prepareFields();
	}

	public RoboBaseAdapter(Context context, int layoutId) {
		this.context = context;
		this.layoutId = layoutId;
		init();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = inflater.inflate(layoutId, null);
		injectViews(convertView);
		return convertView;
	}

	public void injectViews(View view) {
		for (ViewMembersInjector viewMembersInjector : viewsForInjection) {
			viewMembersInjector.reallyInjectMembers(this, view);
		}
	}

	private void prepareFields() {
		for (Field field : this.getClass().getDeclaredFields()) {
			if (field.isAnnotationPresent(InjectView.class)) {
				viewsForInjection.add(new ViewMembersInjector(field, field
						.getAnnotation(InjectView.class)));
			}
		}
	}

	class ViewMembersInjector {
		protected Field field;
		protected InjectView annotation;

		public ViewMembersInjector(Field field, InjectView annotation) {
			this.field = field;
			this.annotation = annotation;
		}

		public void reallyInjectMembers(BaseAdapter adapter, View view) {
			Object value = null;
			try {
				value = view.findViewById(annotation.value());
				if (value == null
//						&& field.getAnnotation(Nullable.class) == null
						) {
					throw new NullPointerException(
							String.format(
									"Can't inject null value into %s.%s when field is not @Nullable",
									field.getDeclaringClass(), field.getName()));
				}
				field.setAccessible(true);
				field.set(adapter, value);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			} catch (IllegalArgumentException f) {
				throw new IllegalArgumentException(String.format(
						"Can't assign %s value %s to %s field %s",
						value != null ? value.getClass() : "(null)", value,
						field.getType(), field.getName()));
			}
		}
	}
}
