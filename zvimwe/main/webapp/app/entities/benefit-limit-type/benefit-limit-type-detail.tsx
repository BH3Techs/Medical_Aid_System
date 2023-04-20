import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './benefit-limit-type.reducer';

export const BenefitLimitTypeDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const benefitLimitTypeEntity = useAppSelector(state => state.benefitLimitType.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="benefitLimitTypeDetailsHeading">
          <Translate contentKey="medicalAidSystemApp.benefitLimitType.detail.title">BenefitLimitType</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{benefitLimitTypeEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="medicalAidSystemApp.benefitLimitType.name">Name</Translate>
            </span>
          </dt>
          <dd>{benefitLimitTypeEntity.name}</dd>
          <dt>
            <span id="active">
              <Translate contentKey="medicalAidSystemApp.benefitLimitType.active">Active</Translate>
            </span>
          </dt>
          <dd>{benefitLimitTypeEntity.active ? 'true' : 'false'}</dd>
        </dl>
        <Button tag={Link} to="/benefit-limit-type" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/benefit-limit-type/${benefitLimitTypeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default BenefitLimitTypeDetail;
