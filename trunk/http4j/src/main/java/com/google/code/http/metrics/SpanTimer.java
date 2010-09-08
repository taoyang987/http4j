/**
 * Copyright (C) 2010 Zhang, Guilin <guilin.zhang@hotmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.code.http.metrics;


/**
 * @author <a href="mailto:guilin.zhang@hotmail.com">Zhang, Guilin</a>
 */
public class SpanTimer extends AbstractAggregatedTimer
		implements AggregatedTimer {

	@Override
	public void aggregate(Timer timer) {
		minStart(timer.getStart());
		maxStop(timer.getStop());
	}

	protected void minStart(long t) {
		if (start.compareAndSet(0, t))
			return;
		long s;
		do {
			s = getStart();
		} while (t < s && !start.compareAndSet(s, t));
	}

	protected void maxStop(long t) {
		long s;
		do {
			s = getStop();
		} while (t > s && !stop.compareAndSet(s, t));
	}
}