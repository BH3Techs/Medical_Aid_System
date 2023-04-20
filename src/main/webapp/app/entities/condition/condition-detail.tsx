import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './condition.reducer';

export const ConditionDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const conditionEntity = useAppSelector(state => state.condition.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="conditionDetailsHeading">
          <Translate contentKey="medicalAidSystemApp.condition.detail.title">Condition</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{conditionEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="medicalAidSystemApp.condition.name">Name</Translate>
            </span>
          </dt>
          <dd>{conditionEntity.name}</dd>
          <dt>
            <span id="details">
              <Translate contentKey="medicalAidSystemApp.condition.details">Details</Translate>
            </span>
          </dt>
          <dd>{conditionEntity.details}</dd>
          <dt>
            <Translate contentKey="medicalAidSystemApp.condition.riskProfile">Risk Profile</Translate>
          </dt>
          <dd>{conditionEntity.riskProfile ? conditionEntity.riskProfile.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/condition" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/condition/${conditionEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ConditionDetail;
