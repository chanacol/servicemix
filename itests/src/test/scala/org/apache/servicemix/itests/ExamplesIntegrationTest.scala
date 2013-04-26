/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.servicemix.itests

import org.junit.runner.RunWith;
import org.ops4j.pax.exam.junit.Configuration;
import org.ops4j.pax.exam.junit.ExamReactorStrategy;
import org.ops4j.pax.exam.junit.JUnit4TestRunner;
import org.ops4j.pax.exam.spi.reactors.EagerSingleStagedReactorFactory
import org.junit.{Ignore, Test}
import org.junit.Assert.{assertTrue,assertNotNull}

/**
 * Tests cases for the examples
 */
@RunWith(classOf[JUnit4TestRunner])
@ExamReactorStrategy(Array(classOf[EagerSingleStagedReactorFactory]))
class ExamplesIntegrationTest extends IntegrationTestSupport with CamelTestSupport {

  @Configuration
  def config() = servicemixTestConfiguration() ++ scalaTestConfiguration

  @Test
  @Ignore("Example currently does not install, cfr. https://issues.apache.org/jira/browse/SM-2183")
  def testActiveMQCamelBlueprintExample = testWithFeature("examples-activemq-camel-blueprint") {
    expect {
      logging.containsMessage(line => line.contains("ActiveMQ-Blueprint-Example set body"))
    }
  }

  @Test
  @Ignore("Example requires more PermGen memory than the default, cfr. https://issues.apache.org/jira/browse/SM-2187")
  def testCamelDroolsExample = testWithFeature("examples-camel-drools") {
    expect {
      logging.containsEvent( _.getLoggerName == "ServeDrink" )
    }
  }

  @Test
  def testCamelOsgiExample : Unit = testWithFeature("examples-camel-osgi") {
    expect {
      logging.containsMessage(line => line.contains("JavaDSL set body"))
    }
    expect {
      logging.containsMessage(line => line.contains("MyTransform set body"))
    }
  }

  @Test
  def testCamelBlueprintExample : Unit = testWithFeature("examples-camel-blueprint") {
    expect {
      logging.containsMessage(line => line.contains("Blueprint-Example set body"))
    }
  }

  @Test
  def testCxfJaxRsExample = testWithFeature("examples-cxf-jaxrs", "camel-http") {
    expect { logging.containsMessage( _.contains("Setting the server's publish address to be /crm")) }
    // TODO: the service appears to be started, but the URLs are not accessible
    // assertTrue(httpGet("http://localhost:8181/cxf/crm/customerservice/customers/123").contains("<Customer><id>123</id>"))
  }

  @Test
  def testCxfJaxRsBlueprintExample = testWithFeature("examples-cxf-jaxrs-blueprint", "camel-http4") {
    expect { logging.containsMessage( _.contains("Setting the server's publish address to be /crm")) }
    assertTrue(requestString("http4://localhost:8181/cxf/crm/customerservice/customers/123").contains("<Customer><id>123</id>"))
  }

  @Test
  def testCxfJaxWsBlueprintExample = testWithFeature("examples-cxf-jaxws-blueprint", "camel-http4") {
    expect { logging.containsMessage( _.contains("Setting the server's publish address to be /HelloWorld")) }
    // TODO: uncomment this once
    // assertNotNull(requestString("http4://localhost:8181/cxf/HelloWorld?wsdl"))
  }

  @Test
  def testCxfOsgi = testWithFeature("examples-cxf-osgi") {
    expect { logging.containsMessage( _.contains("Setting the server's publish address to be /HelloWorld")) }
  }

  @Test
  def testCxfWsRm = testWithFeature("examples-cxf-ws-rm") {
    expect { logging.containsMessage( _.contains("Setting the server's publish address to be /HelloWorld")) }
  }

  @Test
  def testCxfWsSecurityBlueprint = testWithFeature("examples-cxf-ws-security-blueprint") {
    expect { logging.containsMessage( _.contains("Setting the server's publish address to be /HelloWorldSecurity")) }
  }

  @Test
  def testCxfWsSecurityOsgi = testWithFeature("examples-cxf-ws-security-osgi") {
    expect { logging.containsMessage( _.contains("Setting the server's publish address to be /HelloWorldSecurity")) }
  }

  @Test
  def testCxfWsSecuritySignature = testWithFeature("examples-cxf-ws-security-signature") {
    expect { logging.containsMessage( _.contains("Setting the server's publish address to be /HelloWorldSecurity")) }
  }


}
