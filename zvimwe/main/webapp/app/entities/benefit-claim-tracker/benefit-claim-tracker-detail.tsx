import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './benefit-claim-tracker.reducer';

export const BenefitClaimTrackerDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const benefitClaimTrackerEntity = useAppSelector(state => state.benefitClaimTracker.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="benefitClaimTrackerDetailsHeading">
          <Translate contentKey="medicalAidSystemApp.benefitClaimTracker.detail.title">BenefitClaimTracker</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{benefitClaimTrackerEntity.id}</dd>
          <dt>
            <span id="resetDate">
              <Translate contentKey="medicalAidSystemApp.benefitClaimTracker.resetDate">Reset Date</Translate>
            </span>
          </dt>
          <dd>
            {benefitClaimTrackerEntity.resetDate ? (
              <TextFormat value={benefitClaimTrackerEntity.resetDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="nextPossibleClaimDate">
              <Translate contentKey="medicalAidSystemApp.benefitClaimTracker.nextPossibleClaimDate">Next Possible Claim Date</Translate>
            </span>
          </dt>
          <dd>
            {benefitClaimTrackerEntity.nextPossibleClaimDate ? (
              <TextFormat value={benefitClaimTrackerEntity.nextPossibleClaimDate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="currentLimitValue">
              <Translate contentKey="medicalAidSystemApp.benefitClaimTracker.currentLimitValue">Current Limit Value</Translate>
            </span>
          </dt>
          <dd>{benefitClaimTrackerEntity.currentLimitValue}</dd>
          <dt>
            <span id="currentLimitPeriod">
              <Translate contentKey="medicalAidSystemApp.benefitClaimTracker.currentLimitPeriod">Current Limit Period</Translate>
            </span>
          </dt>
          <dd>{benefitClaimTrackerEntity.currentLimitPeriod}</dd>
          <dt>
            <Translate contentKey="medicalAidSystemApp.benefitClaimTracker.benefitLimit">Benefit Limit</Translate>
          </dt>
          <dd>{benefitClaimTrackerEntity.benefitLimit ? benefitClaimTrackerEntity.benefitLimit.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/benefit-claim-tracker" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/benefit-claim-tracker/${benefitClaimTrackerEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default BenefitClaimTrackerDetail;
