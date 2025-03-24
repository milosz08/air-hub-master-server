package pl.miloszgilga.ahms.exception.rest;

import lombok.extern.slf4j.Slf4j;
import org.jmpsl.core.exception.RestServiceServerException;
import org.springframework.http.HttpStatus;
import pl.miloszgilga.i18n.AppLocaleSet;

public class ShopException {
    @Slf4j
    public static class WorkerInShopNotExistException extends RestServiceServerException {
        public WorkerInShopNotExistException(Long wId, Long uId) {
            super(HttpStatus.NOT_FOUND, AppLocaleSet.WORKER_IN_SHOP_NOT_EXIST_EXC);
            log.error("Attemt to get worker by id which does not exist in shop. Followed id: {}, user id: {}", wId, uId);
        }
    }

    @Slf4j
    public static class AccountHasNotEnoughtMoneyException extends RestServiceServerException {
        public AccountHasNotEnoughtMoneyException(long onAccount, int price) {
            super(HttpStatus.BAD_REQUEST, AppLocaleSet.ACCOUNT_HAS_NOT_ENOUGHT_MONEY_EXC);
            log.error("Attempt to buy item without enought money on account. On account: {}, item cost: {}", onAccount, price);
        }
    }

    @Slf4j
    public static class WorkerNotExistException extends RestServiceServerException {
        public WorkerNotExistException(Long nonExistingWorkerId) {
            super(HttpStatus.NOT_FOUND, AppLocaleSet.WORKER_NOT_EXIST_EXC);
            log.error("Attemt to get worker by id which does not exist. Followed id: {}", nonExistingWorkerId);
        }
    }
}
