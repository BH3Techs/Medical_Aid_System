import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './risk-profile.reducer';

export const RiskProfileDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const riskProfileEntity = useAppSelector(state => state.riskProfile.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="riskProfileDetailsHeading">
          <Translate contentKey="medicalAidSystemApp.riskProfile.detail.title">RiskProfile</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{riskProfileEntity.id}</dd>
          <dt>
            <span id="totalRiskScore">
              <Translate contentKey="medicalAidSystemApp.riskProfile.totalRiskScore">Total Risk Score</Translate>
            </span>
          </dt>
          <dd>{riskProfileEntity.totalRiskScore}</dd>
          <dt>
            <span id="lifeStyle">
              <Translate contentKey="medicalAidSystemApp.riskProfile.lifeStyle">Life Style</Translate>
            </span>
          </dt>
          <dd>{riskProfileEntity.lifeStyle}</dd>
        </dl>
        <Button tag={Link} to="/risk-profile" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/risk-profile/${riskProfileEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default RiskProfileDetail;
