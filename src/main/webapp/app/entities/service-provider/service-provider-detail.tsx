import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './service-provider.reducer';

export const ServiceProviderDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const serviceProviderEntity = useAppSelector(state => state.serviceProvider.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="serviceProviderDetailsHeading">
          <Translate contentKey="medicalAidSystemApp.serviceProvider.detail.title">ServiceProvider</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{serviceProviderEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="medicalAidSystemApp.serviceProvider.name">Name</Translate>
            </span>
          </dt>
          <dd>{serviceProviderEntity.name}</dd>
          <dt>
            <span id="aHFOZNumber">
              <Translate contentKey="medicalAidSystemApp.serviceProvider.aHFOZNumber">A HFOZ Number</Translate>
            </span>
          </dt>
          <dd>{serviceProviderEntity.aHFOZNumber}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="medicalAidSystemApp.serviceProvider.description">Description</Translate>
            </span>
          </dt>
          <dd>{serviceProviderEntity.description ? 'true' : 'false'}</dd>
          <dt>
            <Translate contentKey="medicalAidSystemApp.serviceProvider.contactDetails">Contact Details</Translate>
          </dt>
          <dd>{serviceProviderEntity.contactDetails ? serviceProviderEntity.contactDetails.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/service-provider" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/service-provider/${serviceProviderEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ServiceProviderDetail;
