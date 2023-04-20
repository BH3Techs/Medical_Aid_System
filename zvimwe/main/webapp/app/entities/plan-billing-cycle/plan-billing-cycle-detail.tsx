import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './plan-billing-cycle.reducer';

export const PlanBillingCycleDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const planBillingCycleEntity = useAppSelector(state => state.planBillingCycle.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="planBillingCycleDetailsHeading">
          <Translate contentKey="medicalAidSystemApp.planBillingCycle.detail.title">PlanBillingCycle</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{planBillingCycleEntity.id}</dd>
          <dt>
            <span id="periodUnit">
              <Translate contentKey="medicalAidSystemApp.planBillingCycle.periodUnit">Period Unit</Translate>
            </span>
          </dt>
          <dd>{planBillingCycleEntity.periodUnit}</dd>
          <dt>
            <span id="periodValue">
              <Translate contentKey="medicalAidSystemApp.planBillingCycle.periodValue">Period Value</Translate>
            </span>
          </dt>
          <dd>{planBillingCycleEntity.periodValue}</dd>
          <dt>
            <span id="dateConfiguration">
              <Translate contentKey="medicalAidSystemApp.planBillingCycle.dateConfiguration">Date Configuration</Translate>
            </span>
          </dt>
          <dd>{planBillingCycleEntity.dateConfiguration}</dd>
          <dt>
            <span id="billingDate">
              <Translate contentKey="medicalAidSystemApp.planBillingCycle.billingDate">Billing Date</Translate>
            </span>
          </dt>
          <dd>{planBillingCycleEntity.billingDate}</dd>
          <dt>
            <Translate contentKey="medicalAidSystemApp.planBillingCycle.plans">Plans</Translate>
          </dt>
          <dd>{planBillingCycleEntity.plans ? planBillingCycleEntity.plans.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/plan-billing-cycle" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/plan-billing-cycle/${planBillingCycleEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PlanBillingCycleDetail;
