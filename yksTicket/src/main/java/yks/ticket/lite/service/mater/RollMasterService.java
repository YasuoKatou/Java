package yks.ticket.lite.service.mater;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import yks.ticket.lite.dao.master.RollGroupMasterDao;
import yks.ticket.lite.dao.master.RollItemMasterDao;
import yks.ticket.lite.dto.RollMasterResponseDto;

/**
 * ロールマスタサービス.
 *
 * @author yasuokatou (YKS)
 * @since 0.0.1
 */
@Service
public class RollMasterService {
	/** ロールグループマスタDao. */
	@Autowired private RollGroupMasterDao rollGroupMasterDao;
	/** ロール項目マスタDao. */
	@Autowired private RollItemMasterDao rollItemMasterDao;

	/**
	 * ロールマスタを取得する.
	 *
	 * @return ロールマスタ取得レスポンスDto.
	 * @since 0.0.1
	 */
	public RollMasterResponseDto getRollMaster() {
		// ロールグループマスタを取得
		List<RollMasterResponseDto.Group> group = new ArrayList<>();
		this.rollGroupMasterDao.findAll().forEach(groupEntity -> {
			group.add(RollMasterResponseDto.Group.builder()
					.id(groupEntity.getId())
					.name(groupEntity.getName())
					.build());
		});
		// ロール項目マスタを取得
		List<RollMasterResponseDto.Item> item = new ArrayList<>();
		this.rollItemMasterDao.findAll().forEach(itemEntity -> {
			item.add(RollMasterResponseDto.Item.builder()
					.id(itemEntity.getId())
					.name(itemEntity.getName())
					.group_id(itemEntity.getGroup_id())
					.build());
		});

		return RollMasterResponseDto.builder()
				.group(group)
				.item(item)
				.build();
	}
}