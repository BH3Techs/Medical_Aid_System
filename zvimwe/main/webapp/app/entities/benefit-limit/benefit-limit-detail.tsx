import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './benefit-limit.reducer';

export const BenefitLimitDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const benefitLimitEntity = useAppSelector(state => state.benefitLimit.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="benefitLimitDetailsHeading">
          <Translate contentKey="medicalAidSystemApp.benefitLimit.detail.title">BenefitLimit</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{benefitLimitEntity.id}</dd>
          <dt>
            <span id="limitValue">
              <Translate contentKey="medicalAidSystemApp.benefitLimit.limitValue">Limit Value</Translate>
            </span>
          </dt>
          <dd>{benefitLimitEntity.limitValue}</dd>
          <dt>
            <span id="limitPeriodUnit">
              <Translate contentKey="medicalAidSystemApp.benefitLimit.limitPeriodUnit">Limit Period Unit</Translate>
            </span>
          </dt>
          <dd>{benefitLimitEntity.limitPeriodUnit}</dd>
          <dt>
            <span id="limitPeriodValue">
              <Translate contentKey="medicalAidSystemApp.benefitLimit.limitPeriodValue">Limit Period Value</Translate>
            </span>
          </dt>
          <dd>{benefitLimitEntity.limitPeriodValue}</dd>
          <dt>
            <span id="active">
              <Translate contentKey="medicalAidSystemApp.benefitLimit.active">Active</Translate>
            </span>
          </dt>
          <dd>{benefitLimitEntity.active ? 'true' : 'false'}</dd>
          <dt>
            <Translate contentKey="medicalAidSystemApp.benefitLimit.benefitLimitType">Benefit Limit Type</Translate>
          </dt>
          <dd>{benefitLimitEntity.benefitLimitType ? benefitLimitEntity.benefitLimitType.id : ''}</dd>
          <dt>
            <Translate contentKey="medicalAidSystemApp.benefitLimit.planBenefit">Plan Benefit</Translate>
          </dt>
          <dd>{benefitLimitEntity.planBenefit ? benefitLimitEntity.planBenefit.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/benefit-limit" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/benefit-limit/${benefitLimitEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default BenefitLimitDetail;
