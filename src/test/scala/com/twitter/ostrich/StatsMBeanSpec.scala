/*
 * Copyright 2009 Twitter, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.twitter.ostrich

import java.lang.management.ManagementFactory
import javax.{management => jmx}
import scala.collection.{immutable, JavaConversions}
import scala.util.Sorting
import com.twitter.xrayspecs.Time
import com.twitter.xrayspecs.TimeConversions._
import net.lag.extensions._
import org.specs._


object StatsMBeanSpec extends Specification {
  "StatsMBean" should {
    val mbeanServer = ManagementFactory.getPlatformMBeanServer()
    val name = "com.example.foo"

    doBefore {
      Stats.clearAll()
      StatsMBean.clear(name)
      StatsMBean(name)
    }

    def getMBean() = {
      val mbeans = JavaConversions.asSet(mbeanServer.queryMBeans(new jmx.ObjectName("com.example.foo:*"), null))
      mbeans.size mustEqual 1
      mbeans.toList.head
    }

    def getAttributeNames(mbean: jmx.ObjectInstance) = {
      val mbeanInfo = mbeanServer.getMBeanInfo(mbean.getObjectName())
      Sorting.stableSort(mbeanInfo.getAttributes().map { _.getName() }.toList).toList
    }

    "report counters" in {
      Stats.incr("emo_tears")
      Stats.incr("clown_tears", 2)

      val mbean = getMBean()
      getAttributeNames(mbean) mustEqual List("counter_clown_tears", "counter_emo_tears")

      mbeanServer.getAttribute(mbean.getObjectName(), "counter_clown_tears") mustEqual 2
      mbeanServer.getAttribute(mbean.getObjectName(), "counter_emo_tears") mustEqual 1
    }

    "report timings" in {
      Stats.time("procrastinate") {
        Time.advance(10.millis)
      }

      val mbean = getMBean()
      getAttributeNames(mbean) mustEqual List("timing_procrastinate")

      val x = mbeanServer.getAttribute(mbean.getObjectName(), "timing_procrastinate").asInstanceOf[Map[String, Long]]
      x("average") must be_>=(10L)
      x("count") mustEqual 1
    }
  }
}
