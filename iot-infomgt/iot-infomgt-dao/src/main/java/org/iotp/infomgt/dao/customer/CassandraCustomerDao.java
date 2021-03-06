/*******************************************************************************
 * Copyright 2017 osswangxining@163.com
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a copy
 * of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations under
 * the License.
 ******************************************************************************/
/**
 * Copyright © 2016-2017 The Thingsboard Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.iotp.infomgt.dao.customer;

import static com.datastax.driver.core.querybuilder.QueryBuilder.eq;
import static com.datastax.driver.core.querybuilder.QueryBuilder.select;
import static org.iotp.infomgt.dao.model.ModelConstants.CUSTOMER_BY_TENANT_AND_TITLE_VIEW_NAME;
import static org.iotp.infomgt.dao.model.ModelConstants.CUSTOMER_TENANT_ID_PROPERTY;
import static org.iotp.infomgt.dao.model.ModelConstants.CUSTOMER_TITLE_PROPERTY;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.iotp.infomgt.dao.DaoUtil;
import org.iotp.infomgt.dao.model.ModelConstants;
import org.iotp.infomgt.dao.model.nosql.CustomerEntity;
import org.iotp.infomgt.dao.nosql.CassandraAbstractSearchTextDao;
import org.iotp.infomgt.dao.util.NoSqlDao;
import org.iotp.infomgt.data.Customer;
import org.iotp.infomgt.data.page.TextPageLink;
import org.springframework.stereotype.Component;

import com.datastax.driver.core.querybuilder.Select;

import lombok.extern.slf4j.Slf4j;
@Component
@Slf4j
@NoSqlDao
public class CassandraCustomerDao extends CassandraAbstractSearchTextDao<CustomerEntity, Customer> implements CustomerDao {

    @Override
    protected Class<CustomerEntity> getColumnFamilyClass() {
        return CustomerEntity.class;
    }

    @Override
    protected String getColumnFamilyName() {
        return ModelConstants.CUSTOMER_COLUMN_FAMILY_NAME;
    }

    @Override
    public List<Customer> findCustomersByTenantId(UUID tenantId, TextPageLink pageLink) {
        log.debug("Try to find customers by tenantId [{}] and pageLink [{}]", tenantId, pageLink);
        List<CustomerEntity> customerEntities = findPageWithTextSearch(ModelConstants.CUSTOMER_BY_TENANT_AND_SEARCH_TEXT_COLUMN_FAMILY_NAME,
                Arrays.asList(eq(ModelConstants.CUSTOMER_TENANT_ID_PROPERTY, tenantId)),
                pageLink); 
        log.trace("Found customers [{}] by tenantId [{}] and pageLink [{}]", customerEntities, tenantId, pageLink);
        return DaoUtil.convertDataList(customerEntities);
    }

    @Override
    public Optional<Customer> findCustomersByTenantIdAndTitle(UUID tenantId, String title) {
        Select select = select().from(CUSTOMER_BY_TENANT_AND_TITLE_VIEW_NAME);
        Select.Where query = select.where();
        query.and(eq(CUSTOMER_TENANT_ID_PROPERTY, tenantId));
        query.and(eq(CUSTOMER_TITLE_PROPERTY, title));
        CustomerEntity customerEntity = findOneByStatement(query);
        Customer customer = DaoUtil.getData(customerEntity);
        return Optional.ofNullable(customer);
    }

}
