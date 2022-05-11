package com.ptc.computation;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.*;

@Data
@AllArgsConstructor
public class Computation {

	private List<Integer> numbers;

	private Character operator;

	private Integer result;

	private Integer position;

	public Computation(String[] row) {
		this.numbers = Arrays.stream(row).skip(1).filter(item-> !item.isEmpty()).map(Integer::parseInt).toList();
		this.operator = row[0].charAt(0);
		this.result = this.compute();
		this.position = null;
	}

	public Computation(List<Integer> numbers, Character operator, Integer position) {
		this.numbers = numbers;
		this.operator = operator;
		this.result = this.compute();
		this.position = position;
	}

	public Integer compute() {
		switch (this.operator) {
			case '+':
				return this.numbers.stream().reduce(0, Integer::sum);
			case '-':
				return this.numbers.stream().reduce(0, (a, b) -> a - b);
			case '*':
				return this.numbers.stream().reduce(1, (a, b) -> a * b);
			case '/': {
				Integer reduce = 1;
				List<Integer> numbersSorted = new ArrayList<>(List.copyOf(this.numbers));
				Collections.sort(numbersSorted);
				for (Integer number : numbersSorted) {
					reduce = number / reduce;
				}
				return reduce;
			}
			case '%': {
				Integer reduce = 1;
				List<Integer> numbersSorted = new ArrayList<>(List.copyOf(this.numbers));
				Collections.sort(numbersSorted);
				for (Integer number : numbersSorted) {
					reduce = reduce % number;
				}
				return reduce;
			}
			default:
				return null;
		}
	}

	public String operatorToString() {
		if (this.operator == null)
			return null;
		else if (this.operator == '+')
			return "ADD";
		else if (this.operator == '-')
			return "SUB";
		else if (this.operator == '*')
			return "MULTI";
		else if (this.operator == '/')
			return "DIV";
		else if (this.operator == '%')
			return "MOD";
		return null;
	}

	public String toCSV() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.operatorToString());
		sb.append(";");
		this.numbers.forEach(sb::append);
		sb.append(";");
		sb.append(this.result);
		sb.append(";");
		sb.append(";");
		sb.append(";");
		return sb.toString();
	}

	public String toCSVWithoutOperator() {
		StringBuilder sb = new StringBuilder();
		this.numbers.forEach(sb::append);
		sb.append(";");
		sb.append(this.result);
		sb.append(";");
		sb.append(";");
		sb.append(";");
		return sb.toString();
	}
}
