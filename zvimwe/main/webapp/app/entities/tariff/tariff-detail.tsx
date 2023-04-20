import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './tariff.reducer';

export const TariffDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const tariffEntity = useAppSelector(state => state.tariff.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="tariffDetailsHeading">
          <Translate contentKey="medicalAidSystemApp.tariff.detail.title">Tariff</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{tariffEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="medicalAidSystemApp.tariff.name">Name</Translate>
            </span>
          </dt>
          <dd>{tariffEntity.name}</dd>
          <dt>
            <span id="price">
              <Translate contentKey="medicalAidSystemApp.tariff.price">Price</Translate>
            </span>
          </dt>
          <dd>{tariffEntity.price}</dd>
          <dt>
            <span id="active">
              <Translate contentKey="medicalAidSystemApp.tariff.active">Active</Translate>
            </span>
          </dt>
          <dd>{tariffEntity.active ? 'true' : 'false'}</dd>
          <dt>
            <Translate contentKey="medicalAidSystemApp.tariff.currency">Currency</Translate>
          </dt>
          <dd>{tariffEntity.currency ? tariffEntity.currency.id : ''}</dd>
          <dt>
            <Translate contentKey="medicalAidSystemApp.tariff.benefit">Benefit</Translate>
          </dt>
          <dd>{tariffEntity.benefit ? tariffEntity.benefit.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/tariff" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/tariff/${tariffEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default TariffDetail;
