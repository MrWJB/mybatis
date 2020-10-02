/*
 *    Copyright 2009-2012 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.apache.ibatis.builder;

import java.io.*;
import java.util.List;

import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.domain.blog.ComplexImmutableAuthor;
import org.apache.ibatis.domain.blog.mappers.AuthorMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.submitted.permissions.Resource;
import org.junit.Before;
import org.junit.Test;

public class XmlMapperBuilderTest {

  @Test
  public void shouldSuccessfullyLoadXMLMapperFile() throws Exception {
    Configuration configuration = new Configuration();
    String resource = "org/apache/ibatis/builder/AuthorMapper.xml";
    InputStream inputStream = Resources.getResourceAsStream(resource);
    XMLMapperBuilder builder = new XMLMapperBuilder(inputStream, configuration, resource, configuration.getSqlFragments());
    builder.parse();
  }

  private static SqlSession sqlSession = null;

  @Before
  public void sqlSession() throws IOException {
    String resource = "org/apache/ibatis/builder/MapperConfig.xml";
//    InputStream inputStream = Resources.getResourceAsStream(resource);
//    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
    Reader reader = Resources.getResourceAsReader(resource);
    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);

    sqlSession = sqlSessionFactory.openSession();
  }

  @Test
  public void test01() throws IOException {
    AuthorMapper mapper = sqlSession.getMapper(AuthorMapper.class);
    List<ComplexImmutableAuthor> list = mapper.selectComplexAuthors();
    System.out.println(list.toString());
  }

//  @Test
//  public void shouldNotLoadTheSameNamespaceFromTwoResourcesWithDifferentNames() throws Exception {
//    Configuration configuration = new Configuration();
//    String resource = "org/apache/ibatis/builder/AuthorMapper.xml";
//    InputStream inputStream = Resources.getResourceAsStream(resource);
//    XMLMapperBuilder builder = new XMLMapperBuilder(inputStream, configuration, "name1", configuration.getSqlFragments());
//    builder.parse();
//    InputStream inputStream2 = Resources.getResourceAsStream(resource);
//    XMLMapperBuilder builder2 = new XMLMapperBuilder(inputStream2, configuration, "name2", configuration.getSqlFragments());
//    builder2.parse();
//  }



}
