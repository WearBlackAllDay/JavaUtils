package wearblackallday.swing.components;

import javax.swing.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

public class LTextField<T> extends JTextField {

	protected Function<T, String> mapper;
	protected Function<String, T> parser;
	protected Predicate<String> validator;
	protected Predicate<String> limiter;

	protected T valueCache = null;
	protected Boolean validCache = null;

	protected LTextField(Function<T, String> mapper, Function<String, T> parser) {
		super();
		this.mapper = mapper;
		this.parser = parser;
	}

	protected LTextField(String text, Function<T, String> mapper, Function<String, T> parser) {
		super(text);
		this.mapper = mapper;
		this.parser = parser;
	}

	public Function<T, String> getMapper() {
		return this.mapper;
	}

	public Function<String, T> getParser() {
		return this.parser;
	}

	public Predicate<String> getValidator() {
		return this.validator;
	}

	public Predicate<String> getLimiter() {
		return this.limiter;
	}

	public LTextField<T> enforceValidator() {
		if(this.getLimiter() == null) {
			this.setLimiter(this.getValidator());
		} else {
			this.setLimiter(this.getLimiter().and(this.getValidator()));
		}

		return this;
	}

	public boolean hasValidValue() {
		if(this.validCache == null) {
			if(this.getValidator() != null) {
				return this.validCache = this.getValidator().test(this.getText());
			}

			throw new IllegalStateException("Validator has not been defined");
		}

		return this.validCache;
	}

	public T getValue() {
		if(this.getValidator() != null) {
			return this.hasValidValue() ? this.valueCache = this.getParser().apply(this.getText()) : null;
		}

		return this.valueCache = this.parser.apply(this.getText());
	}

	public LTextField<T> setValue(T value) {
		this.setText(this.getMapper().apply(value));
		this.valueCache = value;
		return this;
	}

	public LTextField<T> setValidator(Predicate<String> validator) {
		this.validator = validator;
		return this;
	}

	public LTextField<T> setLimiter(Predicate<String> limiter) {
		this.limiter = limiter;
		return this;
	}

	@Override
	public void setText(String t) {
		if(this.getLimiter() != null && !this.getLimiter().test(t)) {
			return;
		}

		super.setText(t);
		this.valueCache = null;
		this.validCache = null;
	}

	public LTextField<T> setPrompt(String prompt) {
		this.setText(prompt);

		this.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if(LTextField.this.getText().equals(prompt)) {
					LTextField.this.setText("");
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if(LTextField.this.getText().isEmpty()) {
					LTextField.this.setText(prompt);
				}
			}
		});

		return this;
	}

	public LTextField<T> setToolTip(String tooltip) {
		this.setToolTipText(tooltip);
		return this;
	}

	//========================================================================================================//

	public static LTextField<String> empty() {
		return new LTextField<>(s -> s, s -> s);
	}

	public static LTextField<String> ofString(String text) {
		return new LTextField<>(s -> s, s -> s).setValue(text);
	}

	//========================================================================================================//

	public static <T> LTextField<T> of(Function<String, T> parser) {
		return new LTextField<>(Objects::toString, parser);
	}

	public static <T> LTextField<T> of(Function<T, String> mapper, Function<String, T> parser) {
		return new LTextField<>(mapper, parser);
	}

	public static <T> LTextField<T> of(T value, Function<String, T> parser) {
		return new LTextField<>(Object::toString, parser).setValue(value);
	}

	public static <T> LTextField<T> of(T value, Function<T, String> mapper, Function<String, T> parser) {
		return new LTextField<>(mapper, parser).setValue(value);
	}

	//========================================================================================================//

	public static <T> LTextField<T> ofText(String text, Function<String, T> parser) {
		return new LTextField<>(text, Objects::toString, parser);
	}

	public static <T> LTextField<T> ofText(String text, Function<T, String> mapper, Function<String, T> parser) {
		return new LTextField<>(text, mapper, parser);
	}

	public static <T> LTextField<T> ofText(String text, T value, Function<String, T> parser) {
		return new LTextField<>(text, Object::toString, parser).setValue(value);
	}

	public static <T> LTextField<T> ofText(String text, T value, Function<T, String> mapper, Function<String, T> parser) {
		return new LTextField<>(text, mapper, parser).setValue(value);
	}

	//========================================================================================================//

	public static LTextField<Integer> ofInt() {
		return ofInt(null);
	}

	public static LTextField<Integer> ofInt(int value) {
		return ofInt(Integer.toString(value));
	}

	public static LTextField<Integer> ofInt(int value, int radix) {
		return ofInt(Integer.toString(value, radix));
	}

	public static LTextField<Integer> ofInt(String text) {
		return ofInt(text, 10);
	}

	public static LTextField<Integer> ofInt(String text, int radix) {
		return new LTextField<>(text, String::valueOf, s -> Integer.parseInt(s.trim(), radix)).setValidator(s -> {
			try { Integer.parseInt(s.trim(), radix); return true; }
			catch(NumberFormatException e) { return false; }
		});
	}

	//========================================================================================================//

	public static LTextField<Long> ofLong() {
		return ofLong(null);
	}

	public static LTextField<Long> ofLong(long value) {
		return ofLong(Long.toString(value));
	}

	public static LTextField<Long> ofLong(long value, int radix) {
		return ofLong(Long.toString(value, radix));
	}

	public static LTextField<Long> ofLong(String text) {
		return ofLong(text, 10);
	}

	public static LTextField<Long> ofLong(String text, int radix) {
		return new LTextField<>(text, String::valueOf, s -> Long.parseLong(s.trim(), radix)).setValidator(s -> {
			try { Long.parseLong(s.trim(), radix); return true; }
			catch(NumberFormatException e) { return false; }
		});
	}

	//========================================================================================================//

	public static LTextField<Float> ofFloat() {
		return ofFloat(null);
	}

	public static LTextField<Float> ofFloat(float value) {
		return ofFloat(Float.toString(value));
	}

	public static LTextField<Float> ofFloat(String text) {
		return new LTextField<>(text, String::valueOf, s -> Float.parseFloat(s.trim())).setValidator(s -> {
			try { Float.parseFloat(s.trim()); return true; }
			catch(NumberFormatException e) { return false; }
		});
	}

	//========================================================================================================//

	public static LTextField<Double> ofDouble() {
		return ofDouble(null);
	}

	public static LTextField<Double> ofDouble(double value) {
		return ofDouble(Double.toString(value));
	}

	public static LTextField<Double> ofDouble(String text) {
		return new LTextField<>(text, String::valueOf, s -> Double.parseDouble(s.trim())).setValidator(s -> {
			try { Double.parseDouble(s.trim()); return true; }
			catch(NumberFormatException e) { return false; }
		});
	}

}
