import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './plan-benefit.reducer';

export const PlanBenefitDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const planBenefitEntity = useAppSelector(state => state.planBenefit.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="planBenefitDetailsHeading">
          <Translate contentKey="medicalAidSystemApp.planBenefit.detail.title">PlanBenefit</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{planBenefitEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="medicalAidSystemApp.planBenefit.name">Name</Translate>
            </span>
          </dt>
          <dd>{planBenefitEntity.name}</dd>
          <dt>
            <span id="waitingPeriodUnit">
              <Translate contentKey="medicalAidSystemApp.planBenefit.waitingPeriodUnit">Waiting Period Unit</Translate>
            </span>
          </dt>
          <dd>{planBenefitEntity.waitingPeriodUnit}</dd>
          <dt>
            <span id="waitingPeriodValue">
              <Translate contentKey="medicalAidSystemApp.planBenefit.waitingPeriodValue">Waiting Period Value</Translate>
            </span>
          </dt>
          <dd>{planBenefitEntity.waitingPeriodValue}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="medicalAidSystemApp.planBenefit.description">Description</Translate>
            </span>
          </dt>
          <dd>{planBenefitEntity.description}</dd>
          <dt>
            <span id="active">
              <Translate contentKey="medicalAidSystemApp.planBenefit.active">Active</Translate>
            </span>
          </dt>
          <dd>{planBenefitEntity.active ? 'true' : 'false'}</dd>
          <dt>
            <Translate contentKey="medicalAidSystemApp.planBenefit.plans">Plans</Translate>
          </dt>
          <dd>{planBenefitEntity.plans ? planBenefitEntity.plans.id : ''}</dd>
          <dt>
            <Translate contentKey="medicalAidSystemApp.planBenefit.benefitType">Benefit Type</Translate>
          </dt>
          <dd>{planBenefitEntity.benefitType ? planBenefitEntity.benefitType.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/plan-benefit" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/plan-benefit/${planBenefitEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PlanBenefitDetail;
