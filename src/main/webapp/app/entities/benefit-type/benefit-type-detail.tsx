import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './benefit-type.reducer';

export const BenefitTypeDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const benefitTypeEntity = useAppSelector(state => state.benefitType.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="benefitTypeDetailsHeading">
          <Translate contentKey="medicalAidSystemApp.benefitType.detail.title">BenefitType</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{benefitTypeEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="medicalAidSystemApp.benefitType.name">Name</Translate>
            </span>
          </dt>
          <dd>{benefitTypeEntity.name}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="medicalAidSystemApp.benefitType.description">Description</Translate>
            </span>
          </dt>
          <dd>{benefitTypeEntity.description}</dd>
          <dt>
            <span id="active">
              <Translate contentKey="medicalAidSystemApp.benefitType.active">Active</Translate>
            </span>
          </dt>
          <dd>{benefitTypeEntity.active ? 'true' : 'false'}</dd>
        </dl>
        <Button tag={Link} to="/benefit-type" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/benefit-type/${benefitTypeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default BenefitTypeDetail;
