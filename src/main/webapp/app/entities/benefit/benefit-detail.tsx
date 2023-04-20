import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './benefit.reducer';

export const BenefitDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const benefitEntity = useAppSelector(state => state.benefit.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="benefitDetailsHeading">
          <Translate contentKey="medicalAidSystemApp.benefit.detail.title">Benefit</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{benefitEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="medicalAidSystemApp.benefit.name">Name</Translate>
            </span>
          </dt>
          <dd>{benefitEntity.name}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="medicalAidSystemApp.benefit.description">Description</Translate>
            </span>
          </dt>
          <dd>{benefitEntity.description}</dd>
          <dt>
            <span id="benefitCode">
              <Translate contentKey="medicalAidSystemApp.benefit.benefitCode">Benefit Code</Translate>
            </span>
          </dt>
          <dd>{benefitEntity.benefitCode}</dd>
          <dt>
            <span id="active">
              <Translate contentKey="medicalAidSystemApp.benefit.active">Active</Translate>
            </span>
          </dt>
          <dd>{benefitEntity.active ? 'true' : 'false'}</dd>
          <dt>
            <Translate contentKey="medicalAidSystemApp.benefit.benefitType">Benefit Type</Translate>
          </dt>
          <dd>{benefitEntity.benefitType ? benefitEntity.benefitType.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/benefit" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/benefit/${benefitEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default BenefitDetail;
