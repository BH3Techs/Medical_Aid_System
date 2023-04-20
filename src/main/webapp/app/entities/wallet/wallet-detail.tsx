import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './wallet.reducer';

export const WalletDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const walletEntity = useAppSelector(state => state.wallet.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="walletDetailsHeading">
          <Translate contentKey="medicalAidSystemApp.wallet.detail.title">Wallet</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{walletEntity.id}</dd>
          <dt>
            <span id="name">
              <Translate contentKey="medicalAidSystemApp.wallet.name">Name</Translate>
            </span>
          </dt>
          <dd>{walletEntity.name}</dd>
          <dt>
            <span id="balance">
              <Translate contentKey="medicalAidSystemApp.wallet.balance">Balance</Translate>
            </span>
          </dt>
          <dd>{walletEntity.balance}</dd>
          <dt>
            <span id="ownerIdentifier">
              <Translate contentKey="medicalAidSystemApp.wallet.ownerIdentifier">Owner Identifier</Translate>
            </span>
          </dt>
          <dd>{walletEntity.ownerIdentifier}</dd>
          <dt>
            <span id="ownerType">
              <Translate contentKey="medicalAidSystemApp.wallet.ownerType">Owner Type</Translate>
            </span>
          </dt>
          <dd>{walletEntity.ownerType}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="medicalAidSystemApp.wallet.description">Description</Translate>
            </span>
          </dt>
          <dd>{walletEntity.description}</dd>
          <dt>
            <span id="active">
              <Translate contentKey="medicalAidSystemApp.wallet.active">Active</Translate>
            </span>
          </dt>
          <dd>{walletEntity.active ? 'true' : 'false'}</dd>
          <dt>
            <Translate contentKey="medicalAidSystemApp.wallet.currency">Currency</Translate>
          </dt>
          <dd>{walletEntity.currency ? walletEntity.currency.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/wallet" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/wallet/${walletEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default WalletDetail;
