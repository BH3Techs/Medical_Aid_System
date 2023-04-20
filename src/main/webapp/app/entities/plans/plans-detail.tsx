import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './plans.reducer';

export const PlansDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const plansEntity = useAppSelector(state => state.plans.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="plansDetailsHeading">
          <Translate contentKey="medicalAidSystemApp.plans.detail.title">Plans</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{plansEntity.id}</dd>
          <dt>
            <span id="planCode">
              <Translate contentKey="medicalAidSystemApp.plans.planCode">Plan Code</Translate>
            </span>
          </dt>
          <dd>{plansEntity.planCode}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="medicalAidSystemApp.plans.name">Name</Translate>
            </span>
          </dt>
          <dd>{plansEntity.name}</dd>
          <dt>
            <span id="basePremium">
              <Translate contentKey="medicalAidSystemApp.plans.basePremium">Base Premium</Translate>
            </span>
          </dt>
          <dd>{plansEntity.basePremium}</dd>
          <dt>
            <span id="coverAmount">
              <Translate contentKey="medicalAidSystemApp.plans.coverAmount">Cover Amount</Translate>
            </span>
          </dt>
          <dd>{plansEntity.coverAmount}</dd>
          <dt>
            <span id="coverPeriodUnit">
              <Translate contentKey="medicalAidSystemApp.plans.coverPeriodUnit">Cover Period Unit</Translate>
            </span>
          </dt>
          <dd>{plansEntity.coverPeriodUnit}</dd>
          <dt>
            <span id="coverPeriodValue">
              <Translate contentKey="medicalAidSystemApp.plans.coverPeriodValue">Cover Period Value</Translate>
            </span>
          </dt>
          <dd>{plansEntity.coverPeriodValue}</dd>
          <dt>
            <span id="active">
              <Translate contentKey="medicalAidSystemApp.plans.active">Active</Translate>
            </span>
          </dt>
          <dd>{plansEntity.active ? 'true' : 'false'}</dd>
          <dt>
            <Translate contentKey="medicalAidSystemApp.plans.policy">Policy</Translate>
          </dt>
          <dd>
            {plansEntity.policies
              ? plansEntity.policies.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {plansEntity.policies && i === plansEntity.policies.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="medicalAidSystemApp.plans.planCategory">Plan Category</Translate>
          </dt>
          <dd>{plansEntity.planCategory ? plansEntity.planCategory.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/plans" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/plans/${plansEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PlansDetail;
