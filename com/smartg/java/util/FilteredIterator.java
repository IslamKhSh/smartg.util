/*
 * Copyright (c) Andrey Kuznetsov. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  o Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  o Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 *  o Neither the name of imagero Andrey Kuznetsov nor the names of
 *    its contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.smartg.java.util;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * Allows to filter values of Iterator.
 *
 * @param <E>
 */
public class FilteredIterator<E> implements Enumeration<E>, Iterator<E> {

	private final Iterator<E> iterator;
	private final Predicate<E> predicate;

	private E next;

	@Override
	public boolean hasNext() {
		return next != null;
	}

	@Override
	public E next() {
		E nxt = next;
		next = getNext(iterator);
		return nxt;
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

	public FilteredIterator(Iterator<E> e, Predicate<E> p) {
		this.iterator = Objects.requireNonNull(e);
		this.predicate = Objects.requireNonNull(p);
		this.next = getNext(e);
	}

	private E getNext(Iterator<E> e) {
		while (e.hasNext()) {
			E obj = e.next();
			if (obj != null && predicate.test(obj)) {
				return obj;
			}
		}
		return null;
	}

	@Override
	public boolean hasMoreElements() {
		return next != null;
	}

	@Override
	public E nextElement() {
		E nxt = next;
		next = getNext(iterator);
		return nxt;
	}
}
